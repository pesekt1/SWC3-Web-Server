# Issues with cloud database

- When you are importing your database to the cloud, first you need to export your development database:
    - you can use mysql dump

- Often there are issues with:
    - collation
    - user privileges to create triggers, stored routines, views
    
    You need to manually edit the dump file to fix these issues.
    
## DB Resources on Heroku
- ClearDB (free plan) does not allow triggers and stored routines
- JawsDB allows triggers and stored routines, but you need to delete DEFINER from the dump file. 