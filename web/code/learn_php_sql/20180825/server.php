<?php
header("Content-Type: text/html;charset=utf-8");
/**
 * Created by PhpStorm.
 * User: jixiaoyong
 * Date: 2018/8/24
 * Time: 22:10
 */


$inAjax = $_GET['inAjax'];
$do = $_GET['do'];
$do = $do ? $do : 'default';

$server_host = "localhost";
$database = "my_php_db";
$username = "root";
$password = "123456";

$db_conn = new mysqli($server_host,$username,$password,$database);

//$query_str ="SELECT * FROM my_table";
//print_r( $db_conn->query($query_str));


if (!$inAjax) {
    return false;
}

switch ($do) {
    case 'getMember':
        $user_name =$_GET['username'];
        $result = $db_conn->query("SELECT * FROM my_table WHERE lastname='$user_name'");
        $rs = $result->fetch_array();
        print_r(json_encode($rs));

        break;
    case 'default':
        break;
}

//function echoMsg($msg = "")
//{
//    echo "$msg <br>";
//}