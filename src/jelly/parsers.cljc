(ns jelly.parsers)

(defn affilation [b-data candidate-by-name party-by-name]
  (map-indexed (fn [i, prt] (assoc prt :jelly/id i))
               (reduce (fn [aff row] (conj aff {:candidate/id (:jelly/id (candidate-by-name (:candidate row)))
                                                :party/id     (:jelly/id (party-by-name (:party row)))
                                                :jelly/year   (Integer/parseInt (:year row))}))
                       #{} b-data)))

(defn candidate [b-data]
  (map-indexed (fn [i, cnd] (assoc cnd :jelly/id i))
               (reduce (fn [sts row]
                         (conj sts {:candidate/name (:candidate row)})) #{} b-data)))

(defn party [b-data]
  (map-indexed (fn [i, prt] (assoc prt :jelly/id i))
               (reduce (fn [sts row]
                         (conj sts {:party/name (:party row)})) #{} b-data)))

(defn state [b-data]
  (reduce (fn [sts row] (conj sts {:state/po   (:state_po row)
                                   :state/name (:state row)
                                   :state/fips (:state_fips row)
                                   :state/cen  (:state_cen row)
                                   :state/ic   (:state_ic row)})) #{} b-data))


(defn district [b-data states-by-po]
  (reduce (fn [sts row] (conj sts {:jelly/id    (:district row)
                                   :jelly/state (states-by-po (:state_po row))})) #{} b-data))

(defn vote [b-data candidates-by-name]
  (map-indexed (fn [i, row]
                 {
                  :jelly/id     i
                  :candidate/id (:jelly/id (candidates-by-name (:candidate row)))
                  :vote/votes   (Integer/parseInt (:candidatevotes row))
                  :district/id  [(:state_po row) (:district row)]
                  :jelly/year   (Integer/parseInt (:year row))
                  }) b-data))