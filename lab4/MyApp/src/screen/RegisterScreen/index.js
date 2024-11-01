import React, { useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import { apiService } from '../../api/apiService'; 
import styles from './styles'; 

export function RegisterScreen() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleRegister = async () => {
        try {
            const message = await apiService.register(username, password);
            Alert.alert('Success', message);
            setUsername('');
            setPassword('');
        } catch (error) {
            Alert.alert('Error',error);
        }
    };

    return (
        <View style={styles.container}>
            <TextInput
                placeholder="Username"
                value={username}
                onChangeText={setUsername}
                style={styles.input}
            />
            <TextInput
                placeholder="Password"
                value={password}
                onChangeText={setPassword}
                secureTextEntry
                style={styles.input}
            />
            <Button title="Register" onPress={handleRegister} />
        </View>
    );
}
