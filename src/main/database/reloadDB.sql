\connect webapp;
DROP SCHEMA lr_group CASCADE;
\i createTableWithSchema.sql
\i user-insert.sql;
\i question-insert.sql;
\i answer-insert.sql;
\i questionHaveAnswer-insert.sql
\i category-insert.sql
\i below-insert.sql
\i permission-insert.sql
\i certificate-insert.sql
\i haveCertificate-insert.sql