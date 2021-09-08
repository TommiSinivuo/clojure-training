(ns clojure-training.training-03
  (:require [clojure.string :refer [capitalize join]]))


;;------------------------------------------------------------------------------------------
;; Syntactic sugar: thread first/last
;;------------------------------------------------------------------------------------------

(defn exclaim [s]
  (str s "!"))

(def letters ["h", "e", "l", "l", "o", ",", " ", "w", "o", "r", "l", "d"])

(def greeting {:letters letters :x 1 :y 2 :z 3})

;; join -> capitalize -> exclaim

(exclaim (capitalize (join (:letters greeting))))

(-> greeting
    :letters
    (join)
    (capitalize)
    (exclaim))




;;==========================================================================================
;;
;; Destructuring
;; 
;;==========================================================================================


;;------------------------------------------------------------------------------------------
;; assosiative destructuring
;;------------------------------------------------------------------------------------------

(def client {:name "Super Co."
             :location "Philadelphia"
             :description "The worldwide leader in plastic tableware."})

(let [name (:name client)
      location (:location client)
      description (:description client)]
  (str name location "-" description))

(let [{:keys [name location description]} client]
  (str name location "-" description))
