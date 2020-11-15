;; require loads libs (that aren't already loaded), use does the same
;; plus it refers to their namespaces with clojure.core/refer (so you
;; also get the possibility of using :exclude etc like with
;; clojure.core/refer). Both are recommended for use in ns rather than
;; directly.
