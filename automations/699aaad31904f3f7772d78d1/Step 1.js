/import streamlit as st
import PyPDF2
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

def extract_text(pdf_file):
    reader = PyPDF2.PdfReader(pdf_file)
    text = ""
    for page in reader.pages:
        text += page.extract_text()
    return text

st.title("AI Resume Screening System")

job_description = st.text_area("Paste Job Description Here")

uploaded_files = st.file_uploader("Upload Resumes", type=["pdf"], accept_multiple_files=True)

if uploaded_files and job_description:
    resumes = []
    names = []

    for file in uploaded_files:
        text = extract_text(file)
        resumes.append(text)
        names.append(file.name)

    documents = resumes + [job_description]
    
    vectorizer = TfidfVectorizer()
    vectors = vectorizer.fit_transform(documents)
    
    similarity = cosine_similarity(vectors[-1], vectors[:-1])
    
    scores = list(similarity[0])
    
    ranked = sorted(zip(names, scores), key=lambda x: x[1], reverse=True)
    
    st.subheader("Ranked Candidates:")
    for name, score in ranked:
        st.write(f"{name} - Score: {round(score*100, 2)}%")