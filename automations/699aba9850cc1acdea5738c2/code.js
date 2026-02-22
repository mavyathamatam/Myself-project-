
import streamlit as st
import pandas as pd
import PyPDF2
import nltk
import string
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
nltk.download('punkt')
def extract_text_from_pdf(file):
    reader = PyPDF2.PdfReader(file)
    text = ""
    for page in reader.pages:
        text += page.extract_text()
    return text
def clean_text(text):
    text = text.lower()
    text = text.translate(str.maketrans('', '', string.punctuation))
    return text
st.title("AI Resume Ranking System")
job_description = st.text_area("Enter Job Description")
uploaded_files = st.file_uploader("Upload Resumes (PDF)", accept_multiple_files=True)
if st.button("Rank Resumes"):
    if job_description and uploaded_files:
        resumes = []
        names = []
        for file in uploaded_files:
            text = extract_text_from_pdf(file)
            resumes.append(clean_text(text))
            names.append(file.name)
        job_description = clean_text(job_description)
        documents = resumes + [job_description]
        vectorizer = TfidfVectorizer(stop_words='english')
        tfidf_matrix = vectorizer.fit_transform(documents)
        similarity = cosine_similarity(tfidf_matrix[-1], tfidf_matrix[:-1])
        scores = similarity[0]
        result = pd.DataFrame({
            "Resume": names,
            "Score": scores
        })
        result = result.sort_values(by="Score", ascending=False)
        st.subheader("Ranking Results")
        st.dataframe(result)
    else:
        st.warning("Please enter job description and upload resumes.")
