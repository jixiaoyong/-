<?php
/**
 * Created by PhpStorm.
 * User: jixiaoyong
 * Date: 2018/8/24
 * Time: 20:51
 */

function echoMsg($tag = "")
{
    echo "$tag<br><br>";
}

//echo "hello world";

//echoMsg("hello everyone");

$arr = array("username", "age");
//
//echoMsg(json_encode($arr));
//
//echoMsg(serialize($arr));

$arr_1 = array();
$arr_2 = array();

$arr_1["name"] = "zhang_san";
$arr_1["age"] = 12;

$arr_2["people"]["p1"]["name"] = "p1_name";
$arr_2["people"]["p1"]["age"] = 13;

$arr_2["people"]["p2"]["name"] = "p2_name";
$arr_2["people"]["p2"]["age"] = 14;

//print_r($arr_1);
//print_r($arr_2);

//echoMsg(json_encode($arr_2));

class test_json
{
    private $private_name = "private name";
    protected $protected_name = "protected name";
    public $public_name = "public name";
    public $public_name1 = "public name1";
    public $public_name2 = "public name2";

    public function getPrivate()
    {
        return $this->public_name;
    }
}

$test_json = new test_json();

//echoMsg(json_encode($test_json));

$json2array = "{
  \"public_name\": \"public name\",
  \"public_name1\": \"public name1\",
  \"public_name2\": \"public name2\"
}";
print_r(json_decode($json2array,true));

var_dump(json_decode($json2array));