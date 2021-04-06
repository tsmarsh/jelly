(ns jelly.db
  (:require [jelly.parsers :as p]))

(def db (atom {}))
(def indexes (atom {}))

(defn create-idx [coll idx]
  (reduce (fn [index item]
            (assoc index (idx item) item)) {} coll))

(defn affiliations-by-year-and-name [data]
  (reduce (fn [index aff]
            (assoc index [(:year aff) (:name ((:candiates-by-id indexes) (:candidate aff)))] aff))
          {}
          data))

(defn district-by-id-po [data]
  (reduce (fn [index dist]
            (assoc index [(:jelly/id dist) (get-in dist [:jelly/state :state/po])] dist)) {} data))


(defn build-db [base-data]
  (swap! db assoc :db/states (p/state base-data))
  (swap! db assoc  :db/parties (p/party base-data))
  (swap! indexes assoc :states-by-po (create-idx (:db/states @db) :state/po))
  (swap! db assoc :db/districts (p/district base-data (:states-by-po @indexes)))
  (swap! indexes assoc :district-by-id-po (district-by-id-po (:db/districts @db)))
  (swap! db assoc :db/candidates (p/candidate base-data))
  (swap! indexes assoc :candiates-by-id (create-idx (:db/candidates @db) :jelly/id))
  (swap! indexes assoc :candidates-by-name (create-idx (:db/candidates @db) :candidate/name))
  (swap! indexes assoc :parties-by-name (create-idx (:db/parties @db) :party/name))
  (swap! db assoc :db/affiliations (p/affilation base-data (:candidates-by-name @indexes) (:parties-by-name @indexes)))
  (swap! indexes assoc :affliations-by-year-and-name (affiliations-by-year-and-name (:db/affiliations db)))
  (swap! db assoc :db/votes (p/vote base-data (:candidates-by-name @indexes))))