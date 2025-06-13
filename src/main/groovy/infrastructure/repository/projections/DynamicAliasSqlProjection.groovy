package infrastructure.repository.projections

import grails.compiler.GrailsCompileStatic
import org.hibernate.Criteria
import org.hibernate.criterion.CriteriaQuery
import org.hibernate.criterion.SQLProjection
import org.hibernate.internal.util.StringHelper
import org.hibernate.type.Type

import java.util.regex.Matcher
import java.util.regex.Pattern

@GrailsCompileStatic
class DynamicAliasSqlProjection extends SQLProjection {

    private static final Pattern ALIAS_INTERPOLATE_PATTERN = Pattern.compile("\\{(.*?)\\}")
    private static final int ALIAS_INTERPOLATE_PATTERN_NAME_GROUP = 1d
    private static final String ROOT_ENTITY_CRITERIA_ALIAS = "this"
    private static final String ROOT_ENTITY_SQL_ALIAS = "this_"

    private String sql
    private String groupBy

    DynamicAliasSqlProjection(String sql, String[] columnAliases, Type[] types) {
        this(sql, null, columnAliases, types)
    }

    DynamicAliasSqlProjection(String sql, String groupBy, String[] columnAliases, Type[] types) {
        super(sql, groupBy, columnAliases, types)
        this.sql = sql
        this.groupBy = groupBy
    }

    @Override
    String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery) {
        return replaceAliases(sql, criteria, criteriaQuery)
    }

    @Override
    String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
        return replaceAliases(groupBy, criteria, criteriaQuery)
    }

    private String replaceAliases(String sql, Criteria criteria, CriteriaQuery criteriaQuery) {
        String resolvedSql = StringHelper.replace(sql, "{alias}", criteriaQuery.getSQLAlias(criteria))

        Matcher matchPattern = ALIAS_INTERPOLATE_PATTERN.matcher(sql)
        while (matchPattern.find()) {
            String criteriaAlias = matchPattern.group(ALIAS_INTERPOLATE_PATTERN_NAME_GROUP)
            String sqlAlias = criteriaQuery.getSQLAlias(criteria, (criteriaAlias + "."))

            boolean isNonRootAliasResolvedToRoot = criteriaAlias != ROOT_ENTITY_CRITERIA_ALIAS && sqlAlias == ROOT_ENTITY_SQL_ALIAS
            if (isNonRootAliasResolvedToRoot || !sqlAlias) {
                throw new RuntimeException("O alias [${criteriaAlias}] não foi encontrado.")
            }

            resolvedSql = StringHelper.replace(resolvedSql, "{${criteriaAlias}}", sqlAlias)
        }

        return resolvedSql
    }
}