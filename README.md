# SpringJdbcUtil

SpringJdbcUtil: Spring + configuration + jdbc = command line utility - template project

Output: System.out.println()

This version is configured for Oracle driver.

### How to add ojdbc7.jar to local maven repo

https://www.mkyong.com/maven/how-to-add-oracle-jdbc-driver-in-your-maven-local-repository/
To do this, one need to have Oracle account.

### How to use this template - 1

Starting project with Spring (not Spring Boot, but just Spring), configuration in properties file and additionally the same in
standalone file, which is passed in command line.
Spring jdbcTemplate basic usage.

No logging, just System.out.println().

### How to use this template - 2

At the same time this utility shows how to save long strings into Oracle DB, when VARCHAR2 type max 4000 bytes.
CLOB is used and tested.

The case is: system must persist strings which practically ALL fit into small limit, but possibly may be longer, sometimes.
Example: object with serialized properties.
Is it rational to use CLOB, when 99.9% of records have string that fit into VARCHAR2(4000) ?
ALternative would be to use multistrings, that is to save record with long string as collection of records with counter. 

Test to insert 10000 records into local Oracle database (not implemented here) shows:
27133 ms: VARCHAR2 - 10000 into empty table
25641 ms: CLOB - 10000 into empty table
25983 ms: VARCHAR2 - 10000 into empty table
26675 ms: CLOB - 10000 into empty table
26675 ms: CLOB - 10000 more to table with 10000
24974 ms: VARCHAR2 - 10000 more to table with 10000
23952 ms: VARCHAR2 - 10000 more to table with 20000
25445 ms: CLOB - 10000 more to table with 20000

There is no distinct evidence that CLOB is slower. But if implement multirecord approach, separate index needed to keep records ordered by SEQN field, that will give extra burden.

So solution is to use clob.

Thanks to recent drivers and jdbc - we can use simple code to read/write CLOB without streams, just with string value.






