(ns beetleman.vthread-examples.http-kit
  (:require [promesa.exec :as ex]
            [promesa.core :as p]
            [org.httpkit.client :as client]
            [beetleman.vthread-examples.config :as config]))

(defn http-call []
  (-> @(client/get config/url {:timeout config/timeout :throw? true}) :body count))


(comment


  (time
   (http-call))

  )
