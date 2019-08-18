#!/bin/bash

mv booktracker*jar backup

CIRCLE_TOKEN=`cat api-token`

curl https://circleci.com/api/v1.1/project/github/demoth/booktrackerbackend/latest/artifacts?circle-token=$CIRCLE_TOKEN \
   | grep -o 'https://[^"]*' \
   | sed -e "s/$/?circle-token=$CIRCLE_TOKEN/" \
   | grep jar \
   | wget -v -i -

mv booktracker*.jar* booktracker.jar
