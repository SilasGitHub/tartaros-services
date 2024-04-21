#!/bin/bash

# Output file
output="secrets.yaml"

# Start the YAML file content
echo "apiVersion: v1" > $output
echo "kind: Secret" >> $output
echo "metadata:" >> $output
echo "  name: secrets" >> $output
echo "data:" >> $output

while IFS=' ' read -r key value; do
    # Remove newline characters from the value
    value=$(echo -n "$value" | tr -d '\r\n')

    # Base64 encode the value
    encoded_value=$(echo -n "$value" | base64)

    # Append the key and encoded value to the YAML
    echo "  $key: $encoded_value" >> "$output"
done < secret.txt

echo "Generated secret YAML: $output"