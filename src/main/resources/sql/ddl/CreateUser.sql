CREATE TABLE USER (
  created_by varchar2(25) NOT NULL,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by varchar2(25)  NOT NULL,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  id varchar2(10),
  name varchar2(25) NOT NULL,
  password varchar2(25) NOT NULL,
  constraint pk_user primary key( id )
)