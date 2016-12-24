#!/bin/bash

echo "[!] Clean User"
echo "db.user.remove({})" | mongo > /dev/null

echo "[!] Clean userModule"
echo "db.userModule.remove({})" | mongo > /dev/null

echo "[!] Clean module"
echo "db.module.remove({})" | mongo > /dev/null

echo "[!] Clean session"
echo "db.sessions.remove({})" | mongo > /dev/null

echo "[!] Clean backoffice users"
echo "db.backoffice_user.remove({})" | mongo > /dev/null

echo "[!] Clean notifications" 
echo "db.notification.remove({})" | mongo > /dev/null
echo "[+] OKAY!"
