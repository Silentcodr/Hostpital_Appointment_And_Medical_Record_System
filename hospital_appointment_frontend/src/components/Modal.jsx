import React from 'react';
import { X } from 'lucide-react';

const Modal = ({ isOpen, onClose, title, children }) => {
    if (!isOpen) return null;

    return (
        <div style={{
            position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
            backgroundColor: 'rgba(15, 23, 42, 0.7)', backdropFilter: 'blur(8px)',
            display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 1000,
            animation: 'fadeIn 0.2s ease-out'
        }}>
            <div className="glass-card" style={{
                width: '100%', maxWidth: '500px', padding: '2rem', position: 'relative',
                animation: 'slideUp 0.3s ease-out'
            }}>
                <button
                    onClick={onClose}
                    style={{
                        position: 'absolute', top: '1.5rem', right: '1.5rem',
                        background: 'rgba(255,255,255,0.1)', border: 'none', color: 'white',
                        cursor: 'pointer', borderRadius: '50%', padding: '0.25rem',
                        display: 'flex', alignItems: 'center', justifyContent: 'center'
                    }}
                    className="hover:bg-[rgba(255,255,255,0.2)] transition-colors"
                >
                    <X size={20} />
                </button>
                <h2 style={{ marginBottom: '1.5rem', fontSize: '1.5rem', fontWeight: 'bold', color: 'white' }}>{title}</h2>
                {children}
            </div>
        </div>
    );
};

export default Modal;
