(ns rage.utilities
  (:require [cljs.reader :as reader]
            [cljs.js :as c]
            [cljs.analyzer :as ana]
            [clojure.walk :as w]))

(declare clj-to-js)

(defn key-to-js [k]
  "Helper function for clj-to-js."
  (if (satisfies? IEncodeJS k)
    (-clj->js k)
    (if (or (string? k)
            (number? k)
            (keyword? k)
            (symbol? k))
      (clj-to-js k)
      (pr-str k))))

(defn clj-to-js
  "Exactly like clj->js, but use str instead of name on keywords."
   [x]
   (when-not (nil? x)
     (if (satisfies? IEncodeJS x)
       (-clj->js x)
       (cond
         (keyword? x) (str x)
         (symbol? x) (str x)
         (map? x) (let [m (js-obj)]
                    (doseq [[k v] x]
                      (aset m (key-to-js k) (clj-to-js v)))
                    m)
         (coll? x) (let [arr (array)]
                     (doseq [x (map clj->js x)]
                       (.push arr x))
                     arr)
         :else x))))

(defn remove-keyword [ast keyword]
  (w/postwalk #(if (map? %) (dissoc % keyword) %) ast))

(def keywords-to-remove [:env :children :doc])

(defn to-ast [code-str]
  (reduce
    remove-keyword
    (c/analyze-str
      (c/empty-state) code-str nil {:eval c/js-eval :context :expr} identity)
    keywords-to-remove))

(defn ast [code-str]
  (try
    (clj-to-js (to-ast code-str))
    (catch js/Error e (str e))))
