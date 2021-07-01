SELECT name FROM FAMILIES
WHERE id IN(
   SELECT customer_id FROM CUSTOMERS
   WHERE amount=(
        SELECT max(amount) FROM CUSTOMERS
   )
);