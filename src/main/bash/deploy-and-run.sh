#!/bin/bash

set -e
export BUGCLERK_VERSION=${BUGCLERK:-'0.4.1.Final'}
export BUGCLERK_JAR=${BUGCLERK_JAR:-"bugclerk-${BUGCLERK_VERSION}.jar"}
export BUGCLERK_RELEASE_URL=${BUGCLERK_RELEASE_URL:-"https://github.com/jboss-set/bug-clerk/releases/download/bugclerk-${BUGCLERK_VERSION}/${BUGCLERK_JAR}"}
export BUGCLERK_SCRIPT=${BUGCLERK_SCRIPT:-'filter-based-run.sh'}

echo -n "Download BugClerk release from ${BUGCLERK_RELEASE_URL}... "
wget ${BUGCLERK_RELEASE_URL} -O "${BUGCLERK_JAR}"
echo 'Done.'

export BUGCLERK_HOME=$(pwd)

readonly UNZIP_DIR=$(mktemp -d)
unzip "${BUGCLERK_JAR}" -d "${UNZIP_DIR}"
cp "${UNZIP_DIR}/${BUGCLERK_SCRIPT}" .

export FILTER_URL='https://bugzilla.redhat.com/buglist.cgi?cmdtype=dorem&remaction=run&namedcmd=jboss-eap-6.4.z-superset&sharer_id=213224&ctype=csv'

if [ ! -d "${BUGCLERK_HOME}" ]; then
  echo "BugClerk deployment in directory '${BUGCLERK_HOME}' failed..."
  exit 1
fi

./filter-based-run.sh

rm "${BUGCLERK_SCRIPT}" -rf "${UNZIP_DIR}" "${BUGCLERK_JAR}"