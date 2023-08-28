(ns user
  (:require [beetleman.vthread-examples.hato :as hato]
            [beetleman.vthread-examples.http-kit :as http-kit]
            [beetleman.vthread-examples.pg :as pg]
            [beetleman.vthread-examples.runner :as runner]))


(comment

  (do
    (runner/run-vthreads! hato/http-call-vthreads)
    (runner/run-threads! hato/http-call-vthreads)
    (runner/run-threads! hato/http-call))

  (do
    (runner/run-vthreads! http-kit/http-call)
    (runner/run-threads! http-kit/http-call))


  (letfn  [(noop [] (Thread/sleep 1000))]
    (runner/run-vthreads! noop)
    (runner/run-threads! noop))


  ;;TODO: SHOW max_connections;
  (do
    (runner/run-vthreads! pg/run-query)
    (runner/run-threads! pg/run-query))


  )
