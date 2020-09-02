#!/bin/bash

set -e

# setting up environment dependent variables
ENV=${1:-TEST}
GCLOUD_SERVICE_KEY=$(eval echo "\$GCLOUD_SERVICE_KEY_$ENV")
GCLOUD_PROJECT=$(eval echo "\$GCLOUD_PROJECT_$ENV")
# expecting the install directly in the home directory
GCLOUD=${HOME}/google-cloud-sdk/bin/gcloud

echo ${GCLOUD_SERVICE_KEY} | base64 --decode --ignore-garbage > ${HOME}/gcloud-service-key.json

${GCLOUD} auth activate-service-account --key-file ${HOME}/gcloud-service-key.json
${GCLOUD} config set project ${GCLOUD_PROJECT}