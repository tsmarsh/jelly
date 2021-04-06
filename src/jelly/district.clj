(ns jelly.district
  (:require [jelly.core :as c]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn state [b-data]
  (reduce (fn [sts row] (conj sts {:po   (:state_po row)
                                   :name (:state row)
                                   :fips (:state_fips row)
                                   :cen  (:state_cen row)
                                   :ic   (:state_ic row)})) #{} b-data))

(def states (state c/base-data))

(def states-by-po (c/create-idx states :po))

(defn district [b-data]
  (reduce (fn [sts row] (conj sts {:id    (:district row)
                                   :state (states-by-po (:state_po row))})) #{} b-data))

(def districts (district c/base-data))

(def district-by-id-po
  (reduce (fn [index dist]
            (assoc index [(:id dist) (get-in dist [:state :po])] dist)) {} districts))

(defn create-files []
  (map (fn [a]
         (let [file-name (str "./data/state/" (get-in a [:state :po]) "/district/" (:id a))]
           (io/make-parents file-name)
           (spit file-name (json/write-str a))
           file-name))
       districts))