#!/bin/sh

exec 2>error.log

sudo sh load.sh
echo "#1 n = 1"\\r
echo 1 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(1) == $result"\\r
sudo sh unload.sh

sudo sh load.sh
echo "#2 n = 2"\\r
echo 2 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(2) == $result"\\r
sudo sh unload.sh


sudo sh load.sh
echo "#3 n = 13"\\r
echo 13 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(13) == $result"\\r
sudo sh unload.sh


sudo sh load.sh
echo "#5 n = 24"\\r
echo 24 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(24) == $result"\\r
sudo sh unload.sh


sudo sh load.sh
echo "#4 n = -12"\\r
echo -12 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(-12) == $result"\\r
sudo sh unload.sh


sudo sh load.sh
echo "#5 n = 24"\\r
echo 24 > /dev/calc_number
result=$(cat /dev/calc_result)
echo "fib(24) == $result"\\r
sudo sh unload.sh

