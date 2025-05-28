


if [ -f "disneyplus.csv" ]; then
    cp "disneyplus.csv" "/tmp/"
    echo "disneyplus.csv has been moved to /tmp/"
else
    echo "Error: disneyplus.csv not found in the current directory."
    exit 1
fi

exit 0
