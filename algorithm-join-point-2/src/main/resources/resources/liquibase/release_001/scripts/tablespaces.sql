declare

    L_PATH constant         varchar2(128)  := '${tbs_files_dir}'; --'D:/ORADATA/SCORING/DATAFILE';
    L_FILE_SIZE constant    varchar2(10)   := '${initial_tbs_file_size}'; -- '8m';

    L_CMD_TEMPLATE constant varchar2(2048) :=
            'create tablespace #{ts_name} datafile ''#{full_file_name}''' || chr(10) ||
            'size #{file_size} reuse autoextend on next #{file_size}' || chr(10) ||
            'segment space management auto';

begin

    for r in (
        select usr.column_value || '_' || ttp.column_value               ts_name,
               usr.column_value || '_' || ttp.column_value || '.001.dbf' file_name
        from table (sys.odcivarchar2list('jp')) usr
                 cross join table (sys.odcivarchar2list('tab', 'idx', 'lob')) ttp
        )
        loop

            declare
                L_FULL_FILE_NAME constant varchar2(128)  := L_PATH || '/' || r.file_name;
                L_CMD constant            varchar2(2048) :=
                    replace(
                            replace(
                                    replace(L_CMD_TEMPLATE, '#{ts_name}', r.ts_name),
                                    '#{full_file_name}', L_FULL_FILE_NAME
                                ),
                            '#{file_size}', L_FILE_SIZE
                        );
            begin
                --dbms_output.put_line(L_CMD);
                execute immediate L_CMD;
            end;

        end loop;

end;
/
