create table okr_admin.databasechangeloglock
(
    id          number(10) not null,
    locked      number(1)  not null,
    lockgranted timestamp(6),
    lockedby    varchar2(255),
    --
    constraint pk_databasechangeloglock primary key (id)
        using index tablespace jp_idx storage (initial 64 k next 64 k)
)
    tablespace okr_tab
    storage
(
    initial 64k next 64k
)
/
create synonym okr.databasechangeloglock for okr_admin.databasechangeloglock
/

create table rtdm_admin.databasechangeloglock
(
    id          number(10) not null,
    locked      number(1)  not null,
    lockgranted timestamp(6),
    lockedby    varchar2(255),
    --
    constraint pk_databasechangeloglock primary key (id)
        using index tablespace rtdm_tab storage (initial 64 k next 64 k)
)
    tablespace rtdm_tab
    storage
(
    initial 64k next 64k
)
/
create synonym rtdm_7m.databasechangeloglock for rtdm_admin.databasechangeloglock
/

create table okr_admin.databasechangelog
(
    id            VARCHAR2(255) not null,
    author        VARCHAR2(255) not null,
    filename      VARCHAR2(255) not null,
    dateexecuted  TIMESTAMP(6)  not null,
    orderexecuted NUMBER(10)    not null,
    exectype      VARCHAR2(10)  not null,
    md5sum        VARCHAR2(35),
    description   VARCHAR2(255),
    comments      VARCHAR2(255),
    tag           VARCHAR2(255),
    liquibase     VARCHAR2(20)
)
    tablespace okr_tab
    storage
(
    initial 8k next 8k
)
/

create synonym okr.databasechangelog for okr_admin.databasechangelog
/

