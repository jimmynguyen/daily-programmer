<?php
/**
 * /r/dailyprogrammer challenge #333 - hard
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/739j8c/20170929_challenge_333_hard_build_a_web_apidriven/
 */
$file_name = "data.csv";
$data = array_map("str_getcsv", file($file_name));
var_dump($data);
?>
