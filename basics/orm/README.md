1. Single table mapping
    1. create 15[x] 24
    2. read
        1. by id 10[x] 53
        2. by field
            1. equal 15[x] 34
            2. greater 5[x] 4
            3. less 5[x] 4
    3. update 5[x] 20
    4. delete 5[x] 5
2. One-to-one mapping
	1. create record
		1. create child record 10 [x] 34
		2. inject saved child 10
	2. read record 5
	3. update record 5
	4. delete record 5
3. One-to-many mapping
4. Many-to-many mapping


```
+ Entity
	1. single entry load
		1. id，long，int
	  	2. field type， primitive type， char， int， long， double， string 
	  		1. by naming convention 
	  		2. by annotation
	  	3. complex field aggregated values: arrays， set， list， enum
	2. relationship loading
		1. 1:1 ，1:n n:n， 
		2. eager loading
		3. lazy loading
	3. save 
		
+ Query 
```