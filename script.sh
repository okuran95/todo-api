#!/bin/bash

sleep 10

curl  -v -X POST http://db:8091/node/controller/setupServices \
-d 'services=kv%2Cn1ql%2Cindex'

curl  -v -X POST http://db:8091/pools/default \
-d 'memoryQuota=256' \
-d 'indexMemoryQuota=256'

curl  -u "${DB_USERNAME}":"${DB_PASSWORD}" -v -X POST \
http://db:8091/settings/web \
-d 'password=password&username=Administrator&port=SAME'

curl -v -X POST http://db:8091/pools/default/buckets \
-u "${DB_USERNAME}":"${DB_PASSWORD}" \
-d name="${DB_BUCKET}" \
-d bucketType=couchbase \
-d ramQuota=256

curl -v -X POST http://db:8091/pools/default/buckets/"${DB_BUCKET}" \
-u "${DB_USERNAME}":"${DB_PASSWORD}" \
-d autoCompactionDefined=true \
-d parallelDBAndViewCompaction=false \
-d databaseFragmentationThreshold[percentage]=47 \
-d databaseFragmentationThreshold[size]=1073741824 \
-d viewFragmentationThreshold[percentage]=30 \
-d viewFragmentationThreshold[size]=1073741824 \
-d allowedTimePeriod[fromHour]=0 \
-d allowedTimePeriod[fromMinute]=0 \
-d allowedTimePeriod[toHour]=6 \
-d allowedTimePeriod[toMinute]=0 \
-d allowedTimePeriod[abortOutside]=true \
-d purgeInterval=3.0

curl -X POST -v -u "${DB_USERNAME}":"${DB_PASSWORD}" \
http://db:8091/pools/default/buckets/\
"${DB_BUCKET}"/scopes/_default/collections \
-d name=users


curl -X POST -v -u "${DB_USERNAME}":"${DB_PASSWORD}" \
http://db:8091/pools/default/buckets/\
"${DB_BUCKET}"/scopes/_default/collections \
-d name=todos

sleep 10

curl -X POST -u "${DB_USERNAME}":"${DB_PASSWORD}" http://db:8091/settings/indexes -d 'storageMode=plasma'


curl -X POST -u "${DB_USERNAME}":"${DB_PASSWORD}" http://db:8093/query/service -d 'statement=CREATE PRIMARY INDEX ON `app`.`_default`.`users`'

curl -X POST -u "${DB_USERNAME}":"${DB_PASSWORD}" http://db:8093/query/service -d 'statement=CREATE PRIMARY INDEX ON `app`.`_default`.`todos`'


java -jar todo-api.jar \
    --spring.couchbase.host="couchbase://${DB_HOST}" \
    --spring.couchbase.username="${DB_USERNAME}" \
    --spring.couchbase.password="${DB_PASSWORD}" \
    --spring.couchbase.bucket="${DB_BUCKET}" \
    --spring.application.name="${APP_NAME}" \
    --logging.level.root="${LOG_LEVEL}" \
    --spring.token.secret="${SECRET_KEY}"