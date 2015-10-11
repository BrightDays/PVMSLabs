#!/bin/sh

insmod lab4.ko || exit

rm -f /dev/calc_number
rm -f /dev/calc_result

mknod /dev/calc_number c $(awk '$2=="calc_number" {print $1}' /proc/devices) 0
mknod /dev/calc_result c $(awk '$2=="calc_result" {print $1}' /proc/devices) 0

chmod 666 /dev/calc_number
chmod 444 /dev/calc_result
