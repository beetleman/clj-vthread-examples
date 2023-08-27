(ns user
  (:require [beetleman.vthread-examples.hato :as hato]
            [beetleman.vthread-examples.http-kit :as http-kit]
            [beetleman.vthread-examples.runner :as runner]))


(comment

  (runner/run-vthreads! hato/http-call-vthreads)
  (runner/run-threads! hato/http-call-vthreads)
  (runner/run-threads! hato/http-call)

  (runner/run-vthreads! http-kit/http-call)
  (runner/run-threads! http-kit/http-call)




  )
