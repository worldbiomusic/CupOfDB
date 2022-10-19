# CupOfDB
Simple JDBC wrapper for Java


# Features
- Connection pooling
- No need to close connection, statement, and result set (AutoCloseable)
- Support lambda for query and update


# Usage
1. create a pitcher with a name, url, username, and password
```java
Pitcher pitcher = new Pitcher("{pitcher_name}", "{url}", "{username}", "{password}");
```

2. create a spoon and set it to the pitcher
```java
Spoon spoon = new NormalSpoon(pitcher, 10, 3, 5, 1, 1);
pitcher.setSpoon(spoon);
pitcher.stirSpoon(); // start connection pooling thread
```

3. get a cup(connection) from the pitcher
```java
Cup cup = pitcher.getCup();
```

4. query the database
```java
cup.query("SELECT name from USER where id = 1", resultSet -> {
try {
    System.out.println("Name: " + resultSet.getString("name"));
} catch (SQLException e) {
    e.printStackTrace();
}});
```

# Terms
Class names are 
- `Tray` is a Pitcher pool.
- `Pitcher` is a Cup pool.
- `Spoon` is a Pitcher pool manager.
- `Cup` is a connection.


# LICENSE
MIT


# TODO
- [ ] support json option
- [ ] support javadoc
- [ ] register to maven central
- [ ] release jar
- [ ] support pitcher log writer and login timeout