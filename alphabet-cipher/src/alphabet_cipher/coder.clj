(ns alphabet-cipher.coder)

(defn generate-sequence [start end n]
  (apply str (take n (cycle (map char (range (int start) (inc (int end))))))))

(defonce alphabetseq (generate-sequence \a \z 52))

(defn index [message char]
  (.indexOf message char))

(defn get-encoding [keywordseqchar messagechar]
  (let [keywordindex (index alphabetseq (str keywordseqchar))
        messageindex (index alphabetseq (str messagechar))
        startfrom (+ keywordindex messageindex)
        end (+ 1 startfrom)]
    (subs alphabetseq startfrom end)))

(defn get-decoding [keywordseqchar messagechar]
  (let [keywordindex (index alphabetseq (str keywordseqchar))
        subseq (subs alphabetseq keywordindex)
        messageindex (index subseq (str messagechar))
        end (+ 1 messageindex)]
    (subs alphabetseq messageindex end)))

(defn get-decipher [cipherchar messagechar]
  (let [messageindex (index alphabetseq (str messagechar))
        subseq (subs alphabetseq messageindex)
        cipherindex (index subseq (str cipherchar))
        end (+ 1 cipherindex)]
    (subs alphabetseq cipherindex end)))

(defn repeat-keyword-for-message [message keyword]
  (let [len (count message)
        keywordseq (apply str (take len (cycle keyword)))]
    keywordseq))

(defn encode [keyword message]
  (let [keywordseq (repeat-keyword-for-message message keyword)]
  (apply str (map get-encoding keywordseq message))))

(defn decode [keyword message]
  (let [keywordseq (repeat-keyword-for-message message keyword)]
  (apply str (map get-decoding keywordseq message))))

(defn decipher [cipher message]
  (let [keywordcycled (apply str (map get-decipher cipher message))]
    (loop [x 0]
      (if (= cipher (encode (subs keywordcycled 0 (+ x 1)) message))
      (subs keywordcycled 0 (+ x 1))
        (recur (+ x 1))))
    ))

