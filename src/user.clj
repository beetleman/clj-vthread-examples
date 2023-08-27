(ns user
  (:require [beetleman.vthread-examples.hato :as hato]
            [beetleman.vthread-examples.runner :as runner]))


(comment

  (runner/run-vthreads! hato/http-call-vthreads)
  (runner/run-threads! hato/http-call-vthreads)
  (runner/run-threads! hato/http-call)



  )
