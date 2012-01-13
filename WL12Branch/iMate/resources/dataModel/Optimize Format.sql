select
               provisioni0_.*
               provisioni0_1_.
               provisioni0_2_.*
               provisioni0_3_.
               ...
               case when provisioni0_1_.PROV_TASK_ID is not null then 1
                              when provisioni0_2_.PROV_TASK_ID is not null then 2
                              when provisioni0_3_.PROV_TASK_ID is not null then 3
                              when provisioni0_4_.PROV_TASK_ID is not null then 4
                              when provisioni0_5_.PROV_TASK_ID is not null then 5
                              when provisioni0_6_.PROV_TASK_ID is not null then 6
                              when provisioni0_7_.PROV_TASK_ID is not null then 7
                              when provisioni0_8_.PROV_TASK_ID is not null then 8
                              when provisioni0_.PROV_TASK_ID is not null then 0
               end as clazz_
from 	PROV_TASKS provisioni0_ left outer join
	   PROV_BAC_TASKS provisioni0_1_ on provisioni0_.PROV_TASK_ID=provisioni0_1_.PROV_TASK_ID left outer join
	   PROV_ARTILIUM_TASKS provisioni0_2_ on provisioni0_.PROV_TASK_ID=provisioni0_2_.PROV_TASK_ID left outer join
	   PROV_HIQ8000_TASKS provisioni0_3_ on provisioni0_.PROV_TASK_ID=provisioni0_3_.PROV_TASK_ID left outer join
	   PROV_TRANS_RESPONSE_TASKS provisioni0_4_ on provisioni0_.PROV_TASK_ID=provisioni0_4_.PROV_TASK_ID left outer join
	   PROV_SSO_XML_RPC_TASKS provisioni0_5_ on provisioni0_.PROV_TASK_ID=provisioni0_5_.PROV_TASK_ID left outer join
	   PROV_CS2K_TASKS provisioni0_6_ on provisioni0_.PROV_TASK_ID=provisioni0_6_.PROV_TASK_ID left outer join
	   PROV_VOICEMAIL_TASKS provisioni0_7_ on provisioni0_.PROV_TASK_ID=provisioni0_7_.PROV_TASK_ID left outer join
	   PROV_HIS700_TASKS provisioni0_8_ on provisioni0_.PROV_TASK_ID=provisioni0_8_.PROV_TASK_ID
where provisioni0_.PROV_STATE_ID=:1


select 'main',(select 'opt1' from dual)
from dual;


with 
select provisioni0_.PROV_TASK_ID
	from PROV_TASKS provisioni0_ 
	where provisioni0_.PROV_STATE_ID=:1
select provisioni0_.PROV_TASK_ID,provisioni0_1_.MODEM_ENABLED as MODEM1_3_ , null as DN5_
from  PROV_BAC_TASKS provisioni0_1_ where provisioni0_.PROV_TASK_ID=provisioni0_1_.PROV_TASK_ID
union
select provisioni0_.PROV_TASK_ID,null as MODEM1_3_ , provisioni0_2_.DN as DN5_
from  PROV_ARTILIUM_TASKS provisioni0_2_ where provisioni0_.PROV_TASK_ID=provisioni0_2_.PROV_TASK_ID 



select provisioni0_.PROV_TASK_ID as PROV1_2_, provisioni0_.CREATED_BY as CREATED2_2_, (select provisioni0_1_.MODEM_ENABLED as MODEM1_3_  from  PROV_BAC_TASKS provisioni0_1_ where provisioni0_.PROV_TASK_ID=provisioni0_1_.PROV_TASK_ID )
from (select provisioni0_.*
	from PROV_TASKS provisioni0_ 
	where provisioni0_.PROV_STATE_ID=:1);