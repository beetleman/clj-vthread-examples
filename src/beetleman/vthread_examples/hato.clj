(ns beetleman.vthread-examples.hato
  (:require [promesa.exec :as ex]
            [hato.client :as hc]
            [beetleman.vthread-examples.config :as config]))


(def hato-client-vthread (hc/build-http-client {:connect-timeout config/timeout
                                                :executor        @ex/default-vthread-executor
                                                :redirect-policy :always}))


(def hato-client (hc/build-http-client {:connect-timeout config/timeout
                                        :redirect-policy :always}))


(defn http-call []
  (-> (hc/get config/url {:http-client hato-client}) :body count))


(defn http-call-vthreads []
  (-> (hc/get config/url {:http-client hato-client-vthread}) :body count))


(comment


  (time
   (http-call))

  )
