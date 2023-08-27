(ns beetleman.vthread-examples.runner
  (:require [mount.core :as mount :refer [defstate]]
            [promesa.core :as p]
            [promesa.exec :as px]
            [promesa.exec.bulkhead :as pxb]))

(def thread-names (atom #{}))

(defn- collect-thread-names []
  (swap! thread-names
         into
         (->> (Thread/getAllStackTraces)
              keys
              (map #(.getName %)))))

(defn- reset-threads []
  (reset! thread-names #{}))

(defn- print-threads []
  (doseq [n (sort @thread-names)]
    (println n)))

(defstate -cached-executor
  :start (px/cached-executor)
  :stop (.close -cached-executor))

(defstate -vthread-executor
  :start (px/vthread-per-task-executor)
  :stop (.close -vthread-executor))

(defstate vthreads-executor
  :start (pxb/create :permits 100
                     :type :executor
                     :executor -vthread-executor))

(defstate threads-executor
  :start (pxb/create :permits 100
                     :type :executor
                     :executor -cached-executor))


(defn- run-executor! [name get-executor f times]
  (println (format "\n\n=== RUN %s ===" name))
  (println f)
  (reset-threads)
  (mount/stop)
  (Thread/sleep 1000)
  (mount/start)
  (System/gc)

  (time
   @(p/all
     (for [_ (range times)]
       (px/submit! (get-executor)
                   (fn []
                     (f)
                     (collect-thread-names)
                     (f))))))

  (println "Threads:")
  (print-threads)
  (println "Threads count: " (count @thread-names))
  (mount/stop)
  (System/gc))

(defn run-vthreads! [f]
  (run-executor! "VTHREADS" (fn [] vthreads-executor) f 2000))

(defn run-threads! [f]
  (run-executor! "THREADS" (fn [] threads-executor) f 2000))
