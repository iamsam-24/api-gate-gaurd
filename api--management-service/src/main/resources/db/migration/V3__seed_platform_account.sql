

INSERT INTO management_users(
                             organization_id,role_id,username,password_hash,enabled,created_at,updated_at
)
SELECT NULL, roles.id,'gateway-service',crypt('gateadmin123',gen_salt('bf',10)),
       TRUE,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP
FROM roles WHERE roles.name='GATEWAY SERVICE';