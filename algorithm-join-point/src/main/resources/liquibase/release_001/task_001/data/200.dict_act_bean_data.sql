insert into JP.dict_act_bean(BEAN_ID, METHOD, RUN_CONTEXT, RETURN_CONTEXT, DESCRIPTION)
SELECT 'Bean1RunContext', 'Bean1RunContext DESCRIPTION'
from DUAL
union all
SELECT 'Bean2RunContext', 'Bean2RunContext DESCRIPTION'
from DUAL
union all
SELECT 'Bean3RunContext', 'Bean3RunContext DESCRIPTION'
from DUAL
union all
SELECT 'Bean4RunContext', 'Bean4RunContext DESCRIPTION'
from DUAL
union all
SELECT 'Bean5RunContext', 'Bean5RunContext DESCRIPTION'
from DUAL
union all
---
SELECT 'Bean1ReturnContext', 'Bean1ReturnContext DESCRIPTION'
from DUAL
union all
SELECT 'Bean2ReturnContext', 'Bean2ReturnContext DESCRIPTION'
from DUAL
union all
SELECT 'Bean3ReturnContext', 'Bean3ReturnContext DESCRIPTION'
from DUAL
union all
SELECT 'Bean4ReturnContext', 'Bean4ReturnContext DESCRIPTION'
from DUAL
union all
SELECT 'Bean5ReturnContext', 'Bean5ReturnContext DESCRIPTION'
from DUAL --union all