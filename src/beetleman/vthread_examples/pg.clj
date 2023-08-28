(ns beetleman.vthread-examples.pg
  (:require [next.jdbc :as jdbc]
            [next.jdbc.connection :as connection]
            [mount.core :as mount :refer [defstate]]
            [promesa.core :as p])
  (:import (com.zaxxer.hikari HikariDataSource)))

(def db-spec {:dbtype   "postgres"
              :dbname   "postgres"
              :username "postgres"
              :password "password"})


(defstate pool
  :start (connection/->pool HikariDataSource
                            (assoc db-spec
                                   :socketTimeout 30
                                   :maximumPoolSize 120))
  :stop (.close pool))


(defn run-query []
  (with-open [conn (jdbc/get-connection pool)]
    (jdbc/execute-one! conn ["select pg_sleep(1)"])))



(comment
  (mount.core/start #'pool)

  (with-open [conn (jdbc/get-connection pool)]
    (jdbc/execute-one! conn ["SHOW max_connections;"]))

  )
