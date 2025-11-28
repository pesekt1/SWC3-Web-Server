-- This script runs automatically on first container startup with a fresh MySQL volume.
CREATE USER IF NOT EXISTS 'swc3-server'@'%' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON *.* TO 'swc3-server'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
