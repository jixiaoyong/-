<?php
/**
 * Created by PhpStorm.
 * User: jixiaoyong
 * Date: 2018/8/25
 * Time: 13:25
 */
header("Content-type:text/html;charset:utf-8");

include 'DO_NOT_PUBLIC/configs.php';

$request_code = $_REQUEST['request_code'];
$sql_cmd = '';

$db_conn = new mysqli($db_infos['host'], $db_infos['username'], $db_infos['password'], $db_infos['name']);
$table_name = $db_infos['table_name'];


switch ($request_code) {
    case 1://查询

        break;
    case 2://增加
        $sql_cmd = 'INSERT '
            . $table_name
            . '(username,age,email) VALUES(\''
            . $_POST['username'] . '\','
            . $_POST['age'] . ',\''
            . $_POST['email']
            . '\')';

        $db_conn->query($sql_cmd);

        break;
    case 3://删除
        $sql_cmd = 'DELETE FROM '
            . $table_name
            .' WHERE id = \''
            . $_POST['id']
            . '\'';
        $db_conn->query($sql_cmd);
        break;

    case 4:
        $sql_cmd = 'UPDATE '
            . $table_name
            .' SET username=\''
            . $_POST['username']
            .'\','
            .'age=\''
            . $_POST['age']
            .'\','
            .'email=\''
            . $_POST['email']
            .'\''
            .' WHERE id = \''
            . $_POST['id']
            . '\'';
        $db_conn->query($sql_cmd);
        break;
    default:
        return;
        break;
}

$db_query_str = 'CREATE TABLE IF NOT EXISTS '
    . $table_name
    . '(id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(30)  NOT NULL,
age INT(3)  NOT NULL,
email VARCHAR(30)  ,
create_data TIMESTAMP
) ';

$sql_cmd = 'SELECT * FROM table_1';
$json_str = new JsonClass();
$result = $db_conn->query($sql_cmd);

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