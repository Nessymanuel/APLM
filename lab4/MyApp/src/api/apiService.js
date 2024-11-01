
import axios from 'axios';


const API_URL =  'http://192.168.56.1:8080/api';

export const apiService = {
    login: async (username, password) => {
        try {
            const response = await axios.post(`${API_URL}/login`, {
                username,
                password
            });
            return response.data;
        } catch (error) {
            throw error.response.data;
        }
    },
    register: async (username, password) => {
        try {
            const response = await axios.post(`${API_URL}/register`, {
                username,
                password
            });
            return response.data;
        } catch (error) {
            throw error.response.data;
        }
    },
    getUsers: async () => {
        try {
            const response = await axios.get(`${API_URL}/users`);
            return response.data;
        } catch (error) {
            throw error.response.data;
        }
    },
};


