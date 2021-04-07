(ns jelly.schema
  (:require [clojure.spec.alpha :as s]))

(def po_regex #"\w\w")
(def year_regex #"\d\d\d\d")
(def int_regex #"\d*")

(s/def :party/name string?)
(s/def :jelly/id int?)

(s/def ::candidate string?)
(s/def ::party string?)
(s/def ::state_po (s/and string? #(some? (re-matches po_regex %))))
(s/def ::state string?)
(s/def ::state_cen string?)
(s/def ::state_ic string?)
(s/def ::year (s/and string? #(some? (re-matches year_regex %))))
(s/def ::candidatevotes (s/and string? #(some? (re-matches int_regex %))))

(s/def :house/candidate (s/keys :req-un [::candidate]))
(s/def :house/party (s/keys :req-un [::party]))
(s/def :house/district (s/keys :req-un [::state_po ::state ::state_fips ::state_cen ::state_ic ::year]))
(s/def :house/votes (s/keys :req-un [::candidate ::candidatevotes ::state_po ::year]))


(s/def :state/po (s/and string? #(some? (re-matches po_regex %))))
(s/def :state/name string?)
(s/def :state/cen string?)
(s/def :state/ic string?)

(s/def :candidate/name string?)
(s/def :party/name string?)

(s/def :candidate/id :jelly/id)
(s/def :party/id :jelly/id)

(s/def :jelly/candidate (s/keys :req [:candidate/name :candidate/id]))
(s/def :jelly/party (s/keys :req [:party/name :party/id]))

(s/def :jelly/state (s/keys :req [:state/po :state/name :state/cen :state/ic]))
(s/def :jelly/district (s/keys :req [:district/id :state/po]))


(s/def :jelly/affiliation (s/keys :req [:candidate/id :district/id :jelly/year]))

(s/def :jelly/votes (s/keys :req [:jelly/id :candidate/id :vote/votes
                                  :district/id :jelly/year  ]))