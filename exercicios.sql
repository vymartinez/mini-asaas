/* EXERCICIOS */

/* SCRIPT 1 */
SELECT * FROM payment 
WHERE value > 1000;

/* SCRIPT 2 */
SELECT SUM(value), billing_type, YEAR(date_created) FROM payment 
WHERE YEAR(date_created) >= 2020 
GROUP BY billing_type, YEAR(date_created);

/* SCRIPT 3 */
SELECT customer_account.name, payment.value FROM payment 
INNER JOIN customer_account ON payment.customer_account_id = customer_account.id 
WHERE payment.value > 1000 LIMIT 20;

/* SCRIPT 4 */
SELECT LEFT(customer.name, POSITION(' ' IN customer.name) - 1) AS name, status.last_general_status_change as date FROM customer
INNER JOIN customer_register_status status ON status.customer_id = customer.id 
WHERE status.general_approval = "APPROVED"
ORDER BY status.last_general_status_change DESC;