#!/bin/bash

echo "[!] Clean User"
echo "db.user.remove({})" | mongo

echo "[!] Clean userModule"
echo "db.userModule.remove({})" | mongo

echo "[!] Clean module"
echo "db.module.remove({})" | mongo
