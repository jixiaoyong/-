<?php
$servername = "localhost";
$username = "root";
$password = "123456";
$dbname = "my_php_db";
// 创建连接
$conn = new mysqli($servername, $username, $password, $dbname);
// 检测连接
if ($conn->connect_error) {
die("连接失败: " . $conn->connect_error);
}
echo "连接成功";

$sql = "CREATE DATABASE IF NOT EXISTS my_php_db";
if($conn->query($sql) === TRUE){
	echo "数据库创建成功";
}else{
	echo "数据库创建失败：" .$conn->error;
}

$sqlcreatetable = "CREATE TABLE MyGuests (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
firstname VARCHAR(30) NOT NULL,
lastname VARCHAR(30) NOT NULL,
email VARCHAR(50),
reg_date TIMESTAMP
)";

if ($conn->query($sqlcreatetable) === TRUE) {
	echo "表格创建成功";
}else{
	echo "表格创建失败";
}
//关闭连接
$conn->close();
?>