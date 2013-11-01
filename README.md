

MongoDB Course. Home work 
=============================


- MongoDB Course
	General Info
		Info:
			https://education.mongodb.com/courses
			Lenght of course: - 7 weeks
		Using Maven
			to run the project: mvn compile exec:java -Dexec.mainClass=course.BlogController
			to test: mvn test
		Using Gradle
			to rum the project: gradle run
			to test: gradle test
		GitHub on
			https://github.com/romalopes/hw2-3

	- Week 1 - Introduction
		- Suports
			Based on Json Documents(key, values). It is a document database
			In same document Dynamic Schema.  Different collections of data.
		- Scalability and performance VS Funcionality
			Doesn't support joins and transactions. Documents are hierarchical
		- Running
			create a directory %MONGO_HOME%\data\db
			- %MONGO_HOME%/bin/mongo.config
				##store data here
				dbpath=%MONGO_HOME%\data\db
				##all output go here
				logpath=%MONGO_HOME%\log\mongo.log
				##log read and write operations
				diaglog=3

			- Server:
				mongod --dbpath data\db
				mongod --config %MONGO_HOME%\bin\mongo.config
				As a server
					mongod --config %MONGO_HOME%\bin\mongo.config --install
					net start MongoDB
					net stop MongoDB
					mongod --remove (remove the service)
			- Command line: mongo

				Ex:
					mongo test(connects to mongodb already in test collection)
					use test (switches to test collection)
					show collections
					   (collection)  (command)
					db.objects.      save({a:1, b:2, c:3})
					db.objects.      save({a:4, b:5, c:6, d:7, e:['a','b']})
					db.objects.find() <- return all documents
					db.objects.find({a:1}) <- Return all documents that have this element

					db.course.hello.save({'name','maths'})
					db.course.hello.find()

		- Json
			Types of Data
				- Array -> list of items	-> [...]
				- Dictionaries ->associative maps  {key:value} -> {name:"value",city:"value", interests:[ ___, ___, ___]
		- import Bson
			mongorestore -d dbname -c collectionname dir/file.bson
		- import Json
			mongoimport --collection NAME --file NAME.
		- Embeded data into documents
			If the embeded data exceed 16Mb, because each document should have the maximum of 16Mb
		Home Work:
			1 - 42
			2- 2,3,5
			3-366
			4- 2805
	- Week 2 - CRUD
		- CRUD
			Create=Insert.  Read=Find.  Update=Update.  Delete=Remove
			Does not have a language such as SQL.
			- db.people.insert( doc )
			- db.people.find()
			- db.people.findOne({"name": "Anderson"}, {"name":true,"_id":false})
			- all object has a primary key called (_id)
		- BSON - Binary JSON Object
		- Query
			- $gt, $lt,
				- db.people.findOne({name:{$gt:	"B", $lt:"D"}})
				- db.people.findOne({name:{$gt:	"B"}, {name:{$lt:"D"}} }) --Will return all docs less than D. Ignore the first statement.
			- exists
				- To verify if a field exists
				- db.people.findOne({name:{ $exists : true}})  -- return all documents that has the field name.
			- regex
				- db.people.find({name: { $regex : "^A"}}) -> returns everything that starts with A.
				ex:db.users.find( {name: {$regex:"q"}, email: {$exists:true} } )
			- or											//ends with e
				- db.people.find( { $or : [ {name: { $regex : "e$" } } , {age : {$exists: true}]})
				- db.scores.find( {$or : [ {score : {$lt: 50}}, {score : {$gt: 90}} ]})
			- and
			- all - return the documents that have all the specified elements in a array
				db.accounts.find( { favorites : { $all, [ "a", "b", "c" ] } } )
			- in - return all documents that has on of the objects IN a array
				db.accounts.find( { name : { $in, [ "Anderson", "Cida"] } } ) -- returns the docs that have Anderson and Cida
			- Queries
				db.users.find( {email : {work: "romalopes@yahoo.com.br" } } )
				or
				db.users.find( {email.work: "romalopes@yahoo.com.br" } )
			- Cursor

				db.people.insert( {name: "Anderson", email : {work:"111", home:"222"}});
				db.people.insert( {name: "Cida", email : {work:"111", home:"222"}});
				- cur = db.people.find(); null; //NULL avoid the return.
				- cur.limit(5); null; get only 5 elements.
				- cur.sort( {name: -1 }); null;
				- cur.sort( {name: -1 }).limit(5); null;
				- while(cur.hasNext()) printjson(cur.next());

				- db.scores.find( { type : "exam" } ).sort( { score : -1 } ).skip(50).limit(20)
			- count
				db.score.count({type:"exam"})
				- db.scores.count( {"type":"essay", score: {$gt: 90 } } )
			- Update
				- db.people.update( { name: "Anderson"} , {name:"Anderson Lopes", address:"114 Hargrave"})
					- Creates, remove and replace all the attributes.
				- $set
					db.people.update( { name: "Anderson"} , {$set: {age:30}} )
						- set only the values we want to change.
					db.people.update( { name: "Anderson"} , {$inc: {age:3} } )
						- increment 3 in age.
					- ex:
						db.arrays.insert( {_id:0, a:[1,2,3,4]});
						db.arrays.update( {_id:0, {$set : {"a.2" : 5 }});
							change the third element from 3 to 5.
						db.arrays.update( {_id:0, {$push : {a : 6 }});
							add at the end of array
						db.arrays.update( {_id:0, {$pop : {a : 1 }}); //remove the last element
						db.arrays.update( {_id:0, {$pop : {a : -1 }}); //remove the first element
						db.arrays.update( {_id:0, {$pushAll : {a : [6, 7, 8, 9 }});
						db.arrays.update( {_id:0, {$pull : {a : 3} }); //remove the element that the value is 3
						db.arrays.update( {_id:0, {$pullAll : {a : [6, 7, 8, 9 }}); // remove all elements with the values in the second array

				- $unset (remove a field from a collection)
					db.people.update( { name: "Anderson"} , {$unset: {age:1} } )
			- Multi-update
				Update multiple documents
				db.people.update( {} , {$set :{"title": "Dr" } }, {multi: true} );
				db.scores.update ( { score: {$lt:70} }, {$inc: {score:20}}, {multi:true});
			- Remove a database
				- use DATABASE
				- db.dropDatabase()
			- remove
				db.people.remove() //remove everything from the collection.
				db.people.drop() //faster than remove
				db.people.remove( {name:"Alice"})
				db.people.remove( {name: {$gt: "M" }} )
				db.scores.remove( {score: { $lt: 60 }})
			- db.runCommand( {getLastError:1})
				return the last result of a command.  It can be a error, a result of a update and so on.
		With Java
			CRUD
				INSERT DBOBject(interface) . BasicDBObject with is a LinkedHashMap<String, Object>
				 BasicDBObject doc = new BasicDBObject();
					doc.put("userName", "anderson");
					doc.put("birthDate", new Date(2344242));
					doc.put("programmer", true);
					doc.put("age", 8);
					doc.put("languages", Arrays.asList("Java", "C++"));
					doc.put("address", new BasicDBObject("street", "114 Hargrave")
								.append("town", "Paddington")
								.append("zipCode", 2021));


					{  "_id" : "user1",
					   "interests" : [ "basketball", "drumming"]	}
					new BasicDBObject("_id", "user1").append("interests", Arrays.asList("basketball", "drumming"));
				FIND
			        DB courseDB = client.getDB("course");
					DBCollection collection  = courseDB.getCollection("findCriteriaTest");

					collection.drop();

					for(int i=0; i<10; i++) {
						collection.insert(new BasicDBObject( "x", new Random().nextInt(2)).
														   append("y", new Random().nextInt(100)));
					}

					System.out.println("\n Find All");
					DBCursor cursor1 = collection.find();
					try {
						while(cursor1.hasNext()) {
							DBObject all = cursor1.next();
							System.out.println(all);
					   }
					} finally {
						cursor1.close();
					}

					DBObject query = new BasicDBObject("x", 0)
																		//AND in Y
									.append("y", new BasicDBObject("$gt", 10).append("$lt", 90));
	//OR
					QueryBuilder builder = QueryBuilder.start("x").is(0)
							.and("y").greaterThan(10).lessThan(90);


					System.out.println("\n Count");
					long count = collection.count(query); // builder.get()
					System.out.println(count);

					System.out.println("Find One");
					DBObject one = collection.findOne(query); //builder.get()
					System.out.println(one);

					System.out.println("\n Find All");
					DBCursor cursor = collection.find(query); //builder.get()
					try {
						while(cursor.hasNext()) {
							DBObject all = cursor.next();
							System.out.println(all);
						}
					} finally {
						cursor.close();
					}

					for(int i=0; i<10; i++) {
						collection.insert(new BasicDBObject( "_id", i).
                           append("start",
                                   new BasicDBObject("x", new Random().nextInt(90) + 10)
                                                .append("z", new Random().nextInt(90) + 10)
                           ).append("end",
                                new BasicDBObject("x", new Random().nextInt(90) + 10)
                                        .append("z", new Random().nextInt(90) + 10)
                                )
						);
					}

					//QueryBuilder builder = QueryBuilder.start("start.x").greaterThan(50);
					//DBCursor cursor = collection.find(builder.get(),
					//        new BasicDBObject("start.x", true).append("_id", false).append("end.z", true));

					DBCursor  cursor = collection.find()
								.sort(new BasicDBObject("_id", -1)).skip(5).limit(3);
					DBCursor  cursor = collection.find()
								.sort(new BasicDBObject("start.x", -1).append("end.z",1)).skip(5).limit(3);
				UPDATE
				REMOVE
															//query
					collection.update(new BasicDBObject("_id", "alice"),
							new BasicDBObject("age", 35));//update

					collection.update(new BasicDBObject("_id", "alice"),
							new BasicDBObject("gender", "Female"));//update
					//will remove the gender - Female and insert the Title Srs
					collection.update(new BasicDBObject("_id", "alice"),
							new BasicDBObject("$set" , new BasicDBObject("Title", "Srs")));//update

					//To insert, it is needed two last parameters
					collection.update(new BasicDBObject("_id", "frank"),                 //to insert
							new BasicDBObject("$set" , new BasicDBObject("Title", "Sr")), true, false);//update

					collection.update(new BasicDBObject(),//every document                //to update
							new BasicDBObject("$set" , new BasicDBObject("Graduation", "Masters")), false, true);//update

					//collection.remove(new BasicDBObject());
					collection.remove(new BasicDBObject("_id", "alice"));
		- About blog
			View - ftl - freemarker
			controller/model java/spark.
			db.post.insert(
			db.post.insert( 
				{ "author": "Anderson", 
				  "body": "Body", 
				  "title":"title",
				  "comments" : [
					{
						"body": "comment1",
						"email": "email",
						"author": "author1"
					},
					{
						"body": "comment2",
						"email": "emai2",
						"author": "author2"
					}
				  ],
				  "date": ISOData("2012-11-07;47:22),
				  "permalink": "link of post",
				  "tags": [
						"tag1",
						"tag2",
						"tag3"
				  ]
				 }
		- Home Work
			1 - db.grades.find({score:{$gte:65}}).sort( { score : 1 } ).limit(1)
			2 - 124
			3 - fhj837hf9376hgf93hf832jf9
	- Week 3 - Schema Design
		- MongoDB Schema Design
			Application-Driven Schema
			. Features
				Rich Documents
				Pre join data for fast access
				No Joing
				No Constraina like MySql
				Atomic operations(no transactions)
				No Declared Schema
		- MongoDB does not have
			- Foreign key Constraints - Constrains should be guaranteed by application.
			- Transaction
		- MongoDB works with Atomic Operations
			- All the changes you make will be finished before anyone sees the document.
			3 aproaches
				- Reestructure the document to put everything in ONE document
				- Implement Software Locking.
				- Tolerate the inconsistancy.  Sometimes there is no problem if some client wait some time to see the updated value.
		- One to One Relations(1:1)
			Each item correspond to exactly one other item.
			Ex:
				Employee 	--------- 	Resume
				_id:20					_id:30		
				name:"Anderson"			jobs: [		]
				resume:30				education: [		]
										employee: 20
			One item can be embebed in another.  It depends on:
				- Frequency of access: If you access only one and rarely another, it is better to make separately.
				   If put embebed, it will be costy to ready too much data unnecessary.
				- Size of Items: It one element increase too much, it is better to separate them. Max of each document is 16Mb.
				- Atomicity of data: All atomic operations are in a single document. 
					If you want to save all data together, it is better to put them in the same document.
		- One to Many
			Each item can be related to many items from another collection
			Ex:
				City	--------	People
				(_id:"NYC"			{name: "Anderson",
				...}				city: "NYC"}
			It is call one to few if the collection(many) is put inside the first collection;
			Ex:
				Post
				{name:"post",
				 comments:
				       [C1, C2]
				}
		- Many to Many
			Many items can be related to many items from another collection
			Also has the concept of few to few when it is better to embeded one of the collections.  It is bad because it usually creates replication.
			Ex:
				Books 		------			Authors
				{_id:12,					{_id:14,
				title:"Name of book"		author_name:"name",
				authors:[12,14]}			books: [12,13]}
		- Multikey indexes
			Create indexes to make easier to find a element
			Ex:
				students
				{_id:0, "name":"A", teachers:[1,2,3]}
				teachers
				{_id:0, "name":"T"}  //without the relation to students
				db.students.ensureIndex({teachers:1})  //It is specifying that teacher is indexed in students.
				The find.
				db.students.find('teachers':{'$all',[1,3]}) // return all students that have teachers 1,3
				db.students.find('teachers':{'$all',[1,3]}).explain() //shows if there is a index(in cursor)
		- Benefits of Embedding 
			- Read performance with just one round trip to the DB.
			- Remenber the Normalization and a collection can't have more than 16Mb
		- Representing a tree.  Simple, just like a tree in data structure.
			- Ex: Representing categories of ecommerce
			Product		-----------		Category
			category:7					_id: 7
			name:"Name Produ"			name:"Name Cat"
										parent:6  OR ancestors:[1,2,3]
			Ex:
			
			{
			  _id: 34,
			  name : "Snorkeling",
			  parent_id: 12,
			  ancestors: [12, 35, 90]
			}
			Get all the decendants of this category
			db.categories.find({ancestors:34})  //Returns all the categories that has an ancestors == 34 in its array
			Get all the ancestors of this category
			db.categories.find({_id:{'$in':[12,35,90]}}) //Returns all the categories where that _id is in this array
		- When to denormalize
			------- To improve the performance.
			1:1 Embed
				When you don't duplicate(too much)
			1:Many Embed
				When you see one to may and there is no duplication.
			many:many Link
		- How to store large files(GF) with more than 16Mb or blob
			Put them in 2 collections
				- 1. Create a "chuncks" colection. In each document of this collection, put 16Mb of the large file.
					Similiar with old ARJ that each part has a limit to put in the disk
					Plus include a file ID refering to the file collection
				- 2. A file collection that represent the information about file.
			Ex:
				DB database = client.getDB("course");
				FileInputStream inputStream = null;
				GridFS videos = new GridFS(database, "videos);
				try {
					inputStream = new FileInputStream("video.mp4");
				}catch(...){...}
				GridFSInputFile video = video.createFile(inputStream, "video.mp4"); //name can be different from inputStream
				BasicDBObject mata = new BasicDBObject("description", "Vídeo name");
				ArrayList<String> tags = new ArrayList<~>();
				tags.add("Some Tags);
				meta.append("tags",tags);
				video.setMedaData(meta)
				video.save();
			- It is created the two collections files and chuncks with all information about file.
				try: db.course.file.find()  and db.course.chuncks({}, {data:0})
			reference: api.mongodb.org/java/current/  package com.mongodb.gridf
				
				....  To finde the file
				GridFSDBFile gridFile = videos.findOne(new BasicDBObject("filename", "video.mp4");
				FileOutputStream outputSream = new FileOutputStream("video_copy.mp4");
				gridFile.writeTo(outputStream);
		- SideBar: Importing  from a twitter feed
			https://api.twitter.com/1/statuses/user_timeline.json?screen_name=MongoDB?include_rts=1
			Returns the following error because it seems to be deprecated:
			{"errors": [{"message": "The Twitter REST API v1 is no longer active. Please migrate to API v1.1. https://dev.twitter.com/docs/api/1.1/overview.", "code": 68}]}
		
			I run :
			https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=MongoDB?include_rts=1
			Error: {"errors":[{"message":"Bad Authentication data","code":215}]}
			Needs authentication.
		- Home Work
			1 - 
			2 - 

	- Week 4 - Performance (indexes, multiple servers)
		- Index	Concepts
			http://docs.mongodb.org/manual/core/indexes-introduction/
			Used to keep the keys ordered. It is costy, take space on disk.  Small percentage 
			With a database too big, it is necessary to go through the entire collection
			Query need to scan the collection.
			Ex: (name, hair_color, date_birth). Order first name, after hair_color, ...
			To use the index (a,b,c)
							b - No.
							a - Yes
							c - No
							a,b - Yes
							a,c - Yes, but just use the index from a.
		Types
			Defauld_id
				Used by _id. Always exists
			Single Field
				An user-defined index on a single field of docuemnt
				http://docs.mongodb.org/master/_images/index-ascending.png
				ex: {score:1}
			Compound Index
				When the index is composed by more than one field
				http://docs.mongodb.org/master/_images/index-compound-key.png
				ex: {userid:1. score:-1}
			Multikey index
				When the index is composed by elements in different levels and the lower level is an array.
				http://docs.mongodb.org/master/_images/index-multikey.png
				ex: {"adr.zip", 1} // {"adr": [ {zip:1111, ...}, {zip:2222,...}]}
			Geospatial Index
				An index that uses planar geometry that use spherical geometry to return results.
				http://docs.mongodb.org/manual/applications/geospatial-indexes/
			Text index
				Supports text
			Hashed Index
				Indexes the hash of the value of a field


		How to create indexes
			db.students.find({"student_id": 500000})
			db.students.ensureIndex({"student_id": 1, "type": -1})  
			//student_id ascendent and type is descendent.
			//It takes time to create.
			db.students.find({"student_id": 500000})
		How to find
			db.system.indexes.find()   --> list the indexes
			db.students.getIndexes()   --> list all indexes from one DB.
		Remove Index
			db.students.dropIndex({"student_id":1})
		Multikey indexes
			When the is a document with a array.
			Creates a index not only for the array, but will create a index point for each item of the array.
			EX:
				use test
				db.bbb.insert({a:1,b:1})	
				db.bbb.ensureIndex({a:1,b:1}, {unique:true})	//Both ascendent
				db.bbb.insert({a:[1,2,3],b:1})	
				db.bbb.insert({a:5,b:[1,2,3]})	
				db.bbb.getIndexes()
				db.bbb.insert({a:[1,2,3],b:[1,2,3]})	
					cannot index parallel arrays [b]  [a]
			instead of unique, can do dropDrup  -> Remove all duplicated elements except one.  This is very dangerous.

		Multi-level
			A index can be multi-level and can start inside a collection
			Ex: 
				db.student.address.phone.type
				db.student.ensureIndex({"phone.type",1}, 'unique': true)
				db.students.ensureIndex({ student_id:1, class_id:1})
		Index when an entry key is missing
			Ex: {a:1, b:1, c:1}
				{a:2, b:3}	 c = null
				{a:4, b:5}   c = null 		Only the first document is indexed.
			It is a sparse Index
				db.test.ensureIndex({c:1}, {unique: true, sparse:true})
			if a call: db.test.find().sort({'c':1}) //will return only one document due to the index.
		Types of indexes: Foreground/background
			Foreground
				Default on mongo DB
				block all the writers 
				fast
			Background
				slower
				does not block the writers
				Can create many index at time per database.
				Better in production.  
				Better in replicated system.
		Explain
			Explain what the command does.
			db.students().find().explain()
		When is an index used?
			db.students.stats()   // stats about a collection
			db.students.totalIndexSize() //size of a indexes
			It is more important that the INDEX than data fit into memory.
		Index Cardinality
			Regular
				1:1
			Sparse
				number of index point<= document
			Multikey
				at least one value in a document that is a array.
				tags:[__, __, __]
				index Points > documents
		How to tell mongoDB what index to use
			db.students.find().hint({a:1, b:1})

			db.students.find().hint({"$natural:1"})  //use no index
		Using Hint in Java
			MongoClient client = new MongoClient();
			DB db - client.getDB("test");
			BasicDBObject query = new BasicDBObject("a", 40000);
			query.append("b", 400000);
			query.append("c", 400000);

			DBCollection c = dbgetCollection("foo");
			DBObject doc = c.find(query).hint("a_1_b-1_c1").explain();
			OR
			BasicDBObject myHint = new BasicDBObject("a",1).append("b",-1).append("c",1);
			DbObject doc = c.find(query).hint(myHint).explain();
			System.out.println (doc);
		Efficiency of index Use
			Index are inefficient in some cases.
			For some operators such as $gt, $lt because the cursor can go though half of collection
			$ne,Regex - have to go thoght all the index.
			ex: db.collection.find({value:{"$gt",100}}, class_id:20);
		Geospatial Indexes ("2D")
			XY -> 2D
			To have information, the document should have a location x,y
			ex: 'location':[x,y]
			ensureIndex({ "location":'2d', type:1})  
			find({location:{$near:[x,y]}}).limit(20) //return the 20th nearest elements 
			
			Geospatial Spheral
				Consider the shape of earth(curvature, spherial model).
				Use Lat x long "radiandos"
				db.runCommand({geoNear: 'stores', near:[50,50], spherical:true, maxDistance:3})
				//maxDistance is in "radiandos"
			Loggind Slow Queries
				Loggind
					Queries above 100ms texts a log
					It is shown in the screen of mongod (server)
				Profiler
					3 levels in System.profile
					- 0 - OFF. Do not log any query (Default)
					- 1 - Log only slow queries
					- 2 - Log all queries. (good for Debuging)
					It is defined when starts the server
						mongod --profile 1 --slows 2 (2miliseconds)
					To commands the profiles logged
						db.getProfilingLevel()
						db.setProfilingStatus()
						db.setProfilingLevel(1,4) //level, time in miliseconds
						db.system.profile.find().prety()
						db.system.profile.find({ns:/school.students/").sort({ts:1}).prety()
											//Query 				//time stamp

						quiz:
						Write the query to look in the system profile collection for all queries that took longer than one second, ordered by timestamp descending.
							db.system.profile.find({millis:{"$gt",1000}}).sort({ts:-1})
			Mongotop
				similar to unix "top"
				command line: $ mongotop 3.  //Stops each 3 second.
			Mongostat
				based on iostat
				Shows the statitics about mongod running.
			Sharding
				Used to split up a large collection among multiple servers.
				Shards deploys multiples servers.  
				APPLICATION connects to a MONGOS that connects to many MONGOD
				Usually application is in the same server as mongoS.
				On shard can have many mongod that are called ReplicaSets
				Insert: Must include the shard key.
				Update/Remove/find: If doesn't pass the shardkey, the command is broadcasted to all mongod.









but://www.gradle.org/docs/current/userguide/userguide_single.html#overview
Cap 8
http://spring.io/docs
http://spring.io/guides
http://spring.io/guides/gs/securing-web/
http://docs.spring.io/spring-social/docs/current/reference/htmlsingle/
http://docs.spring.io/spring-social-twitter/docs/1.0.5.RELEASE/reference/htmlsingle/
http://docs.spring.io/spring-social-facebook/docs/current/reference/htmlsingle/					