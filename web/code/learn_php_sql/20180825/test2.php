<?php
/**
 * Created by PhpStorm.
 * User: jixiaoyong
 * Date: 2018/8/25
 * Time: 9:50
 */
header("ContentType:text/html;charset:utf-8");

$arr_1 = array();
$arr_1['username'] = 'Toms';
$arr_1['age'] = 18;

$arr_2 = array();
$arr_2["people1"]['username'] = 'Shayn';
$arr_2["people1"]['age'] = 18;
$arr_2["people2"]['username'] = 'Json';
$arr_2["people2"]['age'] = 20;

class Arr{

    public $arr = array();

    /**
     * @param array $arr
     */
    public function setArr($arr)
    {
        $this->arr = $arr;
    }

    /**
     * @return array
     */
    public function getArr()
    {
        return $this->arr;
    }
}

$request_code = $_REQUEST['do'];

switch ($request_code) {
    case 1:
        echo json_encode($arr_1);
        break;
    case 2:
        echo json_encode($arr_2);
        break;
    case 3:
        $arr_3 = new Arr();
        $arr_3->setArr($arr_2);
        echo json_encode($arr_3);
}