import { useState, useEffect } from 'react';
import { departmentService } from '../services/apiService';
import { Building2, Search, Edit2, Trash2, Plus } from 'lucide-react';
import Modal from '../components/Modal';

const Departments = () => {
    const [departments, setDepartments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');

    // Modal states
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
    const [currentDept, setCurrentDept] = useState({ departmentName: '' });
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        fetchDepartments();
    }, []);

    const fetchDepartments = async () => {
        try {
            setLoading(true);
            const data = await departmentService.getAllDepartments();
            if (data && data.data) {
                setDepartments(data.data);
            }
        } catch (error) {
            console.error('Error fetching departments:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleOpenModal = (dept = null) => {
        if (dept) {
            setIsEditing(true);
            setCurrentDept(dept);
        } else {
            setIsEditing(false);
            setCurrentDept({ departmentName: '' });
        }
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setCurrentDept({ departmentName: '' });
    };

    const handleSave = async (e) => {
        e.preventDefault();
        try {
            if (isEditing) {
                await departmentService.updateDepartment(currentDept);
            } else {
                await departmentService.createDepartment(currentDept);
            }
            handleCloseModal();
            fetchDepartments();
        } catch (error) {
            console.error('Error saving department:', error);
            alert('Failed to save department.');
        }
    };

    const confirmDelete = (dept) => {
        setCurrentDept(dept);
        setIsDeleteModalOpen(true);
    };

    const handleDelete = async () => {
        try {
            await departmentService.deleteDepartment(currentDept.departmentId);
            setIsDeleteModalOpen(false);
            fetchDepartments();
        } catch (error) {
            console.error('Error deleting department:', error);
            alert('Failed to delete department. It might be associated with doctors.');
        }
    };

    const filteredDepts = departments.filter(d =>
        d.departmentName?.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="p-8">
            <div className="page-header">
                <div>
                    <h1 className="page-title">Hospital Departments</h1>
                    <p className="page-subtitle">Manage organizational units and specialties.</p>
                </div>
                <button className="btn-primary" onClick={() => handleOpenModal()}>
                    <Plus size={18} />
                    Add Department
                </button>
            </div>

            <div className="glass-card p-6 mb-8">
                <div className="input-group" style={{ marginBottom: 0, flexDirection: 'row', alignItems: 'center' }}>
                    <Search size={20} className="text-secondary" style={{ position: 'absolute', marginLeft: '1rem', color: '#94a3b8' }} />
                    <input
                        type="text"
                        placeholder="Search departments..."
                        className="input-field w-full"
                        style={{ paddingLeft: '3rem' }}
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
                </div>
            </div>

            <div className="dashboard-grid">
                {loading ? (
                    <div className="glass-card p-6"><p>Loading departments...</p></div>
                ) : filteredDepts.length === 0 ? (
                    <div className="glass-card p-6" style={{ gridColumn: '1 / -1', textAlign: 'center' }}>
                        <p className="text-secondary">No departments found.</p>
                    </div>
                ) : (
                    filteredDepts.map((dept) => (
                        <div key={dept.departmentId} className="glass-card p-6" style={{ display: 'flex', flexDirection: 'column' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1rem' }}>
                                <div style={{
                                    width: '48px', height: '48px', borderRadius: '12px',
                                    background: 'rgba(239, 68, 68, 0.2)', color: '#ef4444',
                                    display: 'flex', alignItems: 'center', justifyContent: 'center'
                                }}>
                                    <Building2 size={24} />
                                </div>
                                <div style={{ display: 'flex', gap: '0.5rem' }}>
                                    <button onClick={() => handleOpenModal(dept)} style={{ background: 'transparent', border: 'none', color: '#cbd5e1', cursor: 'pointer' }} className="hover:text-white transition-colors">
                                        <Edit2 size={18} />
                                    </button>
                                    <button onClick={() => confirmDelete(dept)} style={{ background: 'transparent', border: 'none', color: '#ef4444', cursor: 'pointer' }} className="hover:text-red-400 transition-colors">
                                        <Trash2 size={18} />
                                    </button>
                                </div>
                            </div>
                            <h3 style={{ fontSize: '1.25rem', fontWeight: 600, color: 'white' }}>{dept.departmentName}</h3>
                            <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem', marginTop: '0.25rem' }}>
                                ID: #{dept.departmentId}
                            </p>
                        </div>
                    ))
                )}
            </div>

            {/* Add/Edit Modal */}
            <Modal isOpen={isModalOpen} onClose={handleCloseModal} title={isEditing ? 'Edit Department' : 'New Department'}>
                <form onSubmit={handleSave} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <div className="input-group">
                        <label>Department Name</label>
                        <input
                            type="text"
                            className="input-field"
                            placeholder="e.g. Cardiology"
                            required
                            value={currentDept.departmentName}
                            onChange={(e) => setCurrentDept({ ...currentDept, departmentName: e.target.value })}
                        />
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem', marginTop: '1rem' }}>
                        <button type="button" className="btn-secondary" onClick={handleCloseModal}>Cancel</button>
                        <button type="submit" className="btn-primary" style={{ padding: '0.75rem 1.5rem' }}>Save</button>
                    </div>
                </form>
            </Modal>

            {/* Delete Confirmation Modal */}
            <Modal isOpen={isDeleteModalOpen} onClose={() => setIsDeleteModalOpen(false)} title="Confirm Delete">
                <p style={{ color: 'var(--text-secondary)', marginBottom: '1.5rem' }}>
                    Are you sure you want to delete the department <strong>{currentDept?.departmentName}</strong>? This action cannot be undone.
                </p>
                <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem' }}>
                    <button className="btn-secondary" onClick={() => setIsDeleteModalOpen(false)}>Cancel</button>
                    <button className="btn-primary" style={{ background: '#ef4444', borderColor: '#ef4444' }} onClick={handleDelete}>Delete</button>
                </div>
            </Modal>
        </div>
    );
};

export default Departments;
