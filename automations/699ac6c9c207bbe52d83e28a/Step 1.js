import streamlit as st
import requests
import sqlite3
import pandas as pd

st.title("🤖 Smart Automation Dashboard")

task = st.text_input("Enter your task")

if st.button("Run Automation"):
    response = requests.post(
        "http://127.0.0.1:5000/automate",
        json={"task": task}
    )
    st.success(response.json()["status"])

# Show logs
conn = sqlite3.connect("tasks.db")
df = pd.read_sql_query("SELECT * FROM tasks", conn)
st.subheader("Task Logs")
st.dataframe(df)