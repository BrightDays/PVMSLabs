#!/bin/sh

exec 2>error.log

sudo sh load.sh
echo "#1 n = 1"\\r
echo 1 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(1) == $result"\\r


echo "#2 n = 2"\\r
echo 2 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(2) == $result"\\r


echo "#3 n = 3"\\r
echo 3 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(3) == $result"\\r


echo "#4 n = 10"\\r
echo 10 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(10) == $result"\\r


echo "#5 n = -13"\\r
echo -13 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(-13) == $result"\\r


sudo sh unload.sh

