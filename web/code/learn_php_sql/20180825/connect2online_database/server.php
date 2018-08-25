<?php
/**
 * Created by PhpStorm.
 * User: jixiaoyong
 * Date: 2018/8/25
 * Time: 13:25
 */
header("Content-type:text/html;charset:utf-8");

include 'DO_NOT_PUBLIC/configs.php';

$request_code = $_GET['request_code'];

switch ($request_code) {
    case 1:

        break;
    default:
        return;
        break;
}

$db_conn = new mysqli($db_infos['host'],$db_infos['username'], $db_infos['password'], $db_infos['name']);

$db_query_str = 'CREATE TABLE IF NOT EXISTS table_1(
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(30)  NOT NULL,
age INT(3)  NOT NULL,
email VARCHAR(30)  ,
create_data TIMESTAMP
) ';

$json_str = new JsonClass();
$result = $db_conn->query('SELECT * FROM table_1');

while ($arr = $result->fetch_array()) {
    $r['id'] = $arr['id'];
    $r['username'] = $arr['username'];
    $r['age'] = $arr['age'];
    $r['email'] = $arr['email'];
    $r['create_time'] = $arr['create_time'];
    $json_str->add($r);
}

print_r(json_encode($json_str));

class JsonClass
{
    public $arr = array();

    private $index = 0;

    public function add($a)
    {
        $this->arr[$this->index] = $a;
        $this->index = $this->index + 1;
    }

}