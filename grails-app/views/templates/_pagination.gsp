<%
    params.page = Integer.valueOf(params.page ?: 1)
    totalCount = totalCount ?: 0
    max = max ?: 10
%>

<atlas-layout alignment="center" inline justify="center" gap="2" mobile-inline>
    <atlas-button
        type="outlined"
        class="js-previous-page-button"
        ${ params.page == 1 ? 'hidden' : '' }
        description="${ params.page - 1 }">
    </atlas-button>
    <atlas-button
        class="js-current-page-button"
        description="${ params.page }">
    </atlas-button>
    <atlas-button
        type="outlined"
        class="js-next-page-button"
        ${ totalCount <= params.page * max ? 'hidden' : '' }
        description="${ params.page + 1 }">
    </atlas-button>
</atlas-layout>